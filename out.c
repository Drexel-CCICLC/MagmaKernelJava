int _exitCode=0;struct Optional{void *value;};int Optional_isEmpty(struct Optional Optional_){return Optional_.value==NULL;}int Optional_isPresent(struct Optional Optional_){return !Optional_isEmpty(Optional_);}void *Optional_get(struct Optional Optional_){return Optional_.value;}void *Optional_getOrElse(void *other,struct Optional Optional_){if(Optional_isPresent(Optional_)){return Optional_get(Optional_);}else{return other;}}void *_throw1(char* throwable){_throw=throwable;return NULL;}void *Optional_getOrThrow(struct Optional Optional_){if(Optional_isPresent(Optional_)){return Optional_get(Optional_);}else{return _throw1("Optional is empty.");}}struct Optional Optional(void *value){struct Optional Optional_={value};return Optional_;}int main(){int x=10;return _exitCode;}