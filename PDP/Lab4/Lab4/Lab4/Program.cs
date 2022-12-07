using System;
using System.Collections.Generic;

using Lab4.Implementation;

namespace Lab4
{
    class Program
    {
        static void Main()
        {
            var hosts = new List<string>
            {
                "www.cs.ubbcluj.ro/~hfpop/teaching/pfl/requirements.html"
            };

            DirectCallback.Run(hosts); 
        }
    }
}
