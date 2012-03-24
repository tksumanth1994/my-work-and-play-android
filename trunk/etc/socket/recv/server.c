#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>

int main()
{

    int server_sockfd, client_sockfd;
    int server_len, client_len;
    struct sockaddr_in server_address;
    struct sockaddr_in client_address;

    server_sockfd = socket(AF_INET, SOCK_STREAM, 0);

    server_address.sin_family = AF_INET;
    server_address.sin_addr.s_addr = INADDR_ANY;
    server_address.sin_port = 5000;
    server_len = sizeof(server_address);
    bind(server_sockfd, (struct sockaddr *) &server_address, server_len);

    listen(server_sockfd, 5);
    while(1)
    {
        char buf[1024];
        printf("server waiting\n");

        client_sockfd =
            accept(server_sockfd, (struct sockaddr *)&client_address, &client_len);
		printf("server accept\n");

		int allreadsize = 0;
		while(1) {
        	int readsize = read(client_sockfd, buf, 1024);
			if (readsize < 0) {
				printf("err\n");
				break;
			} else if(readsize == 0 ) {
				printf("\ndisconnect by client recv size is %d\n", allreadsize);
				break;
			}
			int i = 0;
			int temp = 0;
			for (i= 0;i< readsize;i++) {
				temp = (int)buf[i];
				temp &=0xff;
				printf("%.2x", temp);
			}
			allreadsize += readsize;
		}
		printf("close socket\n");
        close(client_sockfd);
    }
}

