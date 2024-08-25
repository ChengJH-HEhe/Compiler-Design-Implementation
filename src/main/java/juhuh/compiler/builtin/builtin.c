#define bool _Bool
int printf(const char *__restrict format, ...);
int sprintf(char *__restrict s, const char *__restrict format, ...);
int scanf(const char *__restrict format, ...);
int sscanf(const char *__restrict s, const char *__restrict format, ...);
unsigned long strlen(const char *s);
int strcmp(const char *s1, const char *s2);
void* memcpy(void* dest, const void* src, unsigned long n);
void* malloc(unsigned long size);

void print(char* str){
  printf("%s", str);
}

void println(char* str){
  printf("%s\n", str);
}

void printInt(int i){
  printf("%d", i);
}

void printlnInt(int i){
  printf("%d\n", i);
}

char* getString(){
  char* str = (char*)malloc(256);
  scanf("%s", str);
  return str;
}

int getInt(){
  int i;
  scanf("%d", &i);
  return i;
}

char* toString(int i){
  char* str = (char*)malloc(256);
  sprintf(str, "%d", i);
  return str; 
}

int string_length(char* str){
  return strlen(str);
}

char *string_substring(char *str, int left, int right){
  char *sub = (char*)malloc(right - left + 2);
  memcpy(sub, str + left, right - left + 1);
  sub[right - left + 1] = '\0';
  return sub;
}

int string_parseInt(char *str){
  int i;
  sscanf(str, "%d", &i);
  return i;
}

int string_ord(char *str){
  return (int)str[0];
}

char* _add(char *str1, char *str2){
  char *str = (char*)malloc(strlen(str1) + strlen(str2) + 1);
  sprintf(str, "%s%s", str1, str2);
  return str;
}

int _arr_size(void* arr) {
  return ((int*)arr)[-1];
}



