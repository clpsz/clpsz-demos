#include <math.h>
#include <stdio.h>
#include <stdlib.h>

#include "lua.h"
#include "lualib.h"
#include "lauxlib.h"

static int hello_sin(lua_State *L){
    double d = luaL_checknumber(L, 1);
    lua_pushnumber(L, sin(d));
    return 1;
}

static const struct luaL_Reg hello_lib[] = {
    {"hello_sin" , hello_sin},
    {NULL, NULL}
};

int luaopen_hello_lib(lua_State *L){
    luaL_newlib(L, hello_lib);
    return 1;
}


//int main()
//{
//    printf("abc\n");
//    return 0;
//}
