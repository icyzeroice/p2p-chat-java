# UDP Server API Docs

### Remote UDP Server Configure

```toml
[UdpServer]
protocol = "UDP"
ip = "139.199.5.24"
port = 2333
```

### Peers -> Server

- `method`
  - `'SEEK'` ：创建 / 加入聊天室
  - `'UPDATE'` ：选举了新 leader
  - `'LEAVE'` ： 聊天室最后一位退出聊天室

```json
{
    "method": "string",
    "data": {
        "token": "string",
        "inner": {
            "ip": "string",
            "port": "number"
        }
    }

}
```

### Server -> Database



```json
{
    "method": "string",
    "data": {
        "token": "string",
        "public": {
            "ip": "string",
            "port": "number"
        },
        "inner": {
            "ip": "string",
            "port": "number"
        }
    }
}
```

### Database -> Server

> `'SEEK'`

> > Room has not been created.
```json
{
    "role": "LEADER",
    "token": "string",
    "public": {},
    "inner": {}
}
```

> > Room has been created
```json
{
    "role": "MEMBER",
    "token": "string",
    "public": {},
    "inner": {}
}
```

> `'UPDATE'`
```json
{
    "role": "NEW_LEADER",
    "token": "string",
    "public": {},
    "inner": {}
}
```

> `'LEAVE'`
```js
"DELETE"
```

### Server -> Peers

```json
{
    "role": "string",
    "token": "string",
    "public": {},
    "inner": {}
}
```