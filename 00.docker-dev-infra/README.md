
## `example-redis-1` 이 기동되지 않을 때
윈도우의 경우 아래와 같이 해주세요. 네트워크 서비스를 정지 후 재기동해야 합니다.
```bash
$ net stop winnat
Windows NAT Driver 서비스를 잘 멈추었습니다.

$ net start winnat
Windows NAT Driver 서비스가 잘 시작되었습니다.
```