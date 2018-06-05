# P2P Chatting via Java (Fake & Tentative)

> [Warn]: RECONSTRUCTION BOOKED

### Dependencies

* Netty 4.x

### API Docs

- [API Docs](docs/api.md)

### TODO List

- [ ] Use MAVEN & remove .iml of IDEA

- [ ] Reduce hardcode

- [ ] get rid of the central service

- [ ] support NAT

- [ ] RPC

### Architecture

```markdown

              RoomList
+-------+<---+-------+>---.write-------+
| View1 |    | Room1 |                 V
+-------+    +-------+<---.join---<+-------+
                 .                 | Peer1 |
                 .                 +-------+
                 .                     ^
                                       |
                                    .send
                                       |
                                       V     
                                   +-----------+
                                   | Netty UDP |
                                   +-----------+
                                       ^
                                       |    
                                     .send
                                       |	  
              RoomList                 V
+-------+<---+-------+<---.join---<+-------+
| View2 |    | Room1 |             | Peer2 |
+-------+    +-------+             +-------+
                 .   V                 ^
                 .   |                 |
                 .   +---.write--------+
         
```






























