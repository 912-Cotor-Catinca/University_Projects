using Lab4.Model;
using Lab4.Utils;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace Lab4.Implementation
{
    class DirectCallback
    {
        private static List<string> HOSTS;
        public static void Run(List<string> hostnames)
        {
            HOSTS = hostnames;
            for (var i = 0; i < HOSTS.Count; ++i)
            {
                DoStart(i);
            }
        }

        private static void DoStart(object idObject)
        {
            var id = (int)idObject;
            StartClient(HOSTS[id], id);
            Thread.Sleep(2000);
        }

        private static void StartClient(string host, int id)
        {
            var hostInfo = Dns.GetHostEntry(host.Split('/')[0]);
            var ipAddress = hostInfo.AddressList[0];
            var remoteEndpoint = new IPEndPoint(ipAddress, HttpUtils.HTTP_PORT);

            //create the TCP/IP socket
            var client = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

            var state = new StateObject
            {
                socket = client,
                hostname = host.Split('/')[0],
                endpointPath = host.Contains("/") ? host.Substring(host.IndexOf("/")) : "/",
                remoteEndpoint = remoteEndpoint,
                clientId = id
            };

            state.socket.BeginConnect(state.remoteEndpoint, OnConnect, state);
        }

        private static void OnConnect(IAsyncResult ar)
        {
            var state = (StateObject)ar.AsyncState;
            var clientSocket = state.socket;
            var clientId = state.clientId;
            var hostname = state.hostname;

            clientSocket.EndConnect(ar);
            Console.WriteLine("{0}) Socket connected to {1} ({2})", clientId, hostname, clientSocket.RemoteEndPoint);
            var byteData = Encoding.ASCII.GetBytes(HttpUtils.getRequestString(state.hostname, state.endpointPath));

            state.socket.BeginSend(byteData, 0, byteData.Length, 0, OnSend, state);
        }

        private static void OnSend(IAsyncResult ar)
        {
            var state = (StateObject)ar.AsyncState;
            var clientSocket = state.socket;
            var clientId = state.clientId;
            var byteSent = clientSocket.EndSend(ar);
            Console.WriteLine("{0} Sent {1} bytes to server.", clientId, byteSent);

            state.socket.BeginReceive(state.receiveBuffer, 0, StateObject.BUFFER_SIZE, 0, OnReceive, state);
        }

        private static void OnReceive(IAsyncResult ar)
        {
            var state = (StateObject)ar.AsyncState;
            var clientSocket = state.socket;
            var clientId = state.clientId;

            try
            {
                var bytesRead = clientSocket.EndReceive(ar);
                state.responseContent.Append(Encoding.ASCII.GetString(state.receiveBuffer, 0, bytesRead));
                if(!HttpUtils.responseHeaderFullyObtained(state.responseContent.ToString()))
                {
                    clientSocket.BeginReceive(state.receiveBuffer, 0, StateObject.BUFFER_SIZE, 0, OnReceive, state);
                }
                else
                {
                    var responseBody = HttpUtils.getResponseBody(state.responseContent.ToString());
                    var contentLengthHeaderValue = HttpUtils.getContentLength(state.responseContent.ToString());
                    if(responseBody.Length < contentLengthHeaderValue)
                    {
                        clientSocket.BeginReceive(state.receiveBuffer, 0, StateObject.BUFFER_SIZE, 0, OnReceive, state);
                    }
                    else
                    {
                        Console.WriteLine(state.responseContent);

                        clientSocket.Shutdown(SocketShutdown.Both);
                        clientSocket.Close();
                    }
                }

            }catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }
    }
}
