using Lab4.Model;
using Lab4.Utils;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace Lab4.Implementation
{
    class AsyncTasksMechanism
    {
        private static List<string> HOSTS;
        private static List<Task> TASKS;

        public static void Run(List<string> hostnames)
        {
            HOSTS = hostnames;
            TASKS = new List<Task>();

            for (var i = 0; i < HOSTS.Count; ++i)
            {
                TASKS.Add(Task.Factory.StartNew(DoStart, i));
            }

            Task.WaitAll(TASKS.ToArray());
        }

        private static void DoStart(object idObject)
        {
            var id = (int)idObject;
            StartClient(HOSTS[id], id);
        }

        private static async void StartClient(string host, int id)
        {
            var ipHostInfo = Dns.GetHostEntry(host.Split('/')[0]);
            var ipAddress = ipHostInfo.AddressList[0];
            var remoteEndpoint = new IPEndPoint(ipAddress, HttpUtils.HTTP_PORT);

            var client = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
            var state = new StateObject
            {
                socket = client,
                hostname = host.Split('/')[0],
                endpointPath = host.Contains("/") ? host.Substring(host.IndexOf("/")) : "/",
                remoteEndpoint = remoteEndpoint,
                clientId = id
            };

            Connect(state).Wait();

            Send(state, HttpUtils.getRequestString(state.hostname, state.endpointPath)).Wait();

            Receive(state).Wait();

            Console.WriteLine(state.responseContent);
            client.Shutdown(SocketShutdown.Both);
            client.Close();

        }

        private static async Task Connect(StateObject state)
        {
            state.socket.BeginConnect(state.remoteEndpoint, ConnectCallback, state);
            await Task.FromResult<object>(state.connectDone.WaitOne());
        }

        private static void ConnectCallback(IAsyncResult ar)
        {
            var state = (StateObject)ar.AsyncState;
            var clientSocket = state.socket;
            var clientId = state.clientId;
            var hostname = state.hostname;

            clientSocket.EndConnect(ar);

            Console.WriteLine("{0}) Socket connected to {1} ({2})", clientId, hostname, clientSocket.RemoteEndPoint);
            state.connectDone.Set();
        }

        private static async Task Receive(StateObject state)
        {
            state.socket.BeginReceive(state.receiveBuffer, 0, StateObject.BUFFER_SIZE, 0, ReceiveCallback, state);
            await Task.FromResult<object>(state.receiveDone.WaitOne());
        }

        private static void ReceiveCallback(IAsyncResult ar)
        {
            //receive details from connection
            var state = (StateObject)ar.AsyncState;
            var clientSocket = state.socket;

            try
            {
                //read data from the remote device
                var bytesRead = clientSocket.EndReceive(ar);

                //store the received string
                state.responseContent.Append(Encoding.ASCII.GetString(state.receiveBuffer, 0, bytesRead));

                if (!HttpUtils.responseHeaderFullyObtained(state.responseContent.ToString()))
                {
                    clientSocket.BeginReceive(state.receiveBuffer, 0, StateObject.BUFFER_SIZE, 0, ReceiveCallback, state);
                }
                else
                {
                    //get the body
                    var responseBody = HttpUtils.getResponseBody(state.responseContent.ToString());

                    if (responseBody.Length < HttpUtils.getContentLength(state.responseContent.ToString()))
                    {
                        clientSocket.BeginReceive(state.receiveBuffer, 0, StateObject.BUFFER_SIZE, 0, ReceiveCallback, state);
                    }
                    else
                    {
                        state.receiveDone.Set();
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        private static async Task Send(StateObject state, string data)
        {
            var byteDate = Encoding.ASCII.GetBytes(data);
            state.socket.BeginSend(byteDate, 0, byteDate.Length, 0, SendCallback, state);

            await Task.FromResult<object>(state.sendDone.WaitOne());
        }

        private static void SendCallback(IAsyncResult ar)
        {
            var state = (StateObject)ar.AsyncState;
            var clientSocket = state.socket;
            var clientId = state.clientId;

            var bytesSent = clientSocket.EndSend(ar);
            Console.WriteLine("{0}) Sent {1} bytes to server.", clientId, bytesSent);

            state.sendDone.Set();
        }
    }
}
