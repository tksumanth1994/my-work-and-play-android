#include  <sys/types.h>
#include  <sys/socket.h>
#include  <stdio.h>
#include  <netinet/in.h>
#include  <arpa/inet.h>
#include  <unistd.h>
#include  <string.h>


int main(int argc, char **argv)
{

    int sockfd, len, result;
    struct sockaddr_in address;
    char *ch = (char*)argv[1]; 

    sockfd = socket(AF_INET, SOCK_STREAM, 0);

    address.sin_family = AF_INET;
    address.sin_addr.s_addr = inet_addr("127.0.0.1");
    address.sin_port = 5000;
    len = sizeof(address);

    result = connect(sockfd, (struct sockaddr *)&address, len);
    if(result == -1) {
		printf("oops : client1");
	}

	int writesize = 0;
	int l = strlen(ch);
    writesize = write(sockfd, ch, l);
    close(sockfd);
    return 0;
}
