# UDP Server API Docs

### Remote UDP Server Configure

```toml
[UdpServer]
protocol = "UDP"
ip = "139.199.5.24"
port = 2333
```

### type definition

* <host>
```json
{
    "ip": "<string>",
    "port": "<number>"
}
```

### Peers --[SEND]--> Server

- `method`
  - `'SEEK'` ：创建 / 加入聊天室
  - `'UPDATE'` ：选举了新 leader
  - `'LEAVE'` ： 聊天室最后一位退出聊天室

```json
{
    "method": "<string>",
    "data": {
        "token": "<string>",
        "public": "<host>",
        "inner": "<host>"
    }
}
```

### Server --[COMMUNICATE]--> Database

```json
{
    "method": "string",
    "data": {
        "token": "<string>",
        "public": "<host>",
        "inner": "<host>"
    }
}
```

### Database --[COMMUNICATE]--> Server

> `'SEEK'`

> > Room has not been created.

```json
{
    "role": "LEADER",
    "token": "<string>",
    "public": "<host>",
    "inner": "<host>"
}
```

> > Room has been created

```json
{
    "role": "MEMBER",
    "token": "<string>",
    "public": "<host>",
    "inner": "<host>"
}
```

> `'UPDATE'`

```json
{
    "role": "NEW_LEADER",
    "token": "<string>",
    "public": "<host>",
    "inner": "<host>"
}
```

> `'LEAVE'`

```
"DELETE"
```

### Server --[RECEIVE]--> Peers

```json
{
    "role": "<string>",
    "token": "<string>",
    "public": "<host>",
    "inner": "<host>"
}
```
