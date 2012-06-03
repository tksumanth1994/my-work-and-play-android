#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdbool.h>
int mCounter = 0;

bool searchmdat(int fd) {
	char buf[4] = {0, 0, 0, 0};
	char tmp;
	for(;;) {
		int size = read(fd, &tmp, 1);
		mCounter++;
		if (size == 0 ) {
			printf("[%s] read == 0\n", __FUNCTION__);
			return -1;
		} else if  (size < 0) {
			printf("[%s] read err\n", __FUNCTION__);
			return -2;
		}
		buf[3] = tmp; 
		if (!strncmp("mdat", buf, 4)) {
			return 0;
		} else {
			buf[0] = buf[1];
			buf[1] = buf[2];
			buf[2] = buf[3];
		}

	}
}
int readLength(int fd) {
	char buf[4];
	int length;

	int size = read(fd, buf, 4);
	mCounter+=4;
	if (size == 0) {
		printf("[%s] read == 0\n", __FUNCTION__) ;
		return -1;
	} else if ( size < 0) {
		printf("[%s] read < 0\n", __FUNCTION__);
		return -2;
	}
	//printf("%x %x %x %x\n", buf[0], buf[1], buf[2], buf[3]);
	length = (buf[0] << 24) & 0xff000000;
	length |= (buf[1] << 16) & 0xff0000;
	length |= (buf[2] << 8) & 0xff00;
	length |= (buf[3] ) & 0xff;
	return length;

}

int main(int argc, char** argv) 
{
	int fd = open(argv[1], O_RDWR);
	if (fd < 0 ) {
		printf("[%s] open err %s\n", __FUNCTION__, argv[1]);
		return 0;
	}
	int ret  = searchmdat(fd);
	if (ret < 0) {
		printf("[%s]no mdat\n", __FUNCTION__);
		return 0;
	} 

	int dcount =0;
	for (;;) {
		dcount++;
		//printf("c=0x%x\n", mCounter);
		int size = readLength(fd);
		if (size < 0) {
			printf("readLength err\n");
			break;
		}
		//printf("c=0x%x ", mCounter);
		printf("%.4x size = 0x%x\n", dcount, size);
		char * buf = (char*)malloc(size); 
		if (buf == NULL) {
			printf("malloc err\n");
			return 0;
		}

		size = read(fd, buf, size);
		if (size == 0) {
			printf("[%s] read == 0\n", __FUNCTION__) ;
			break;
		} else if ( size < 0) {
			printf("[%s] read < 0\n", __FUNCTION__);
			break;
		}
		free(buf);
	}

	close(fd);
	return 0;
}

