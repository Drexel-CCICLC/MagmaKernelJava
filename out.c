#include <stdio.h>
int _exitCode=0;
struct Internal${};
void Internal$_writeString(char* value,struct Internal$ Internal$_){
	printf("%s",value);
}
struct Internal$ Internal$(){
	struct Internal$ Internal$_={};
	return Internal$_;
}
int main(){
	struct Internal$ Internal=Internal$();
	Internal$_writeString("Hello World!");
	return _exitCode;
}