#include <stdio.h>
#include <stdlib.h>
int* c2(int a0,int b1){int* array=malloc(2*sizeof(int));array[0]=a0;array[1]=b1;return array;}int main(){void** main=malloc(0*sizeof(void*));int* array=c2(3,4);printf("%i",array[0]);printf("%i",array[1]);free(array);return 0;}