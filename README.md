#Plugin
BlockFountain

#Minecraft version
1.12.2

#Author
worldbiomusic

#Version
1.0

#Description
-블럭분수 플러그인

-OP 필요

-가지고 놀기 좋음

#Commands
<>: 필수, 
[]: 선택

```
/bf add <name> [ <block> <power> <period> <blockLifeTime> [<x> <y> <z> | <player>] ]
:블럭분수 생성(업데이트)후 시작 
power: x, y, z방향의 vector 크기
period: 블럭 생성 주기 (초)
blockLifeTime: 블럭이 생긴후 없어지기 까지의 시간 (초)
/bf start <name>
:블럭분수 시작
/bf pause <name>
:블럭분수 잠시 멈춤
/bf remove <name>
:블럭 분수 제거
/bf list
:블럭분수 리스트
/bf tp <name>
:블럭분수의 위치로 이동
/bf info <name>
: print [name, block, power, period, blockLifeTime, x, y, z taskId, state, owner]
:블럭 분수에 대한 정보 출력  
```

-bf add <name> 으로 간단하게 자신의 위치에 블럭분수 생성 가능

-bf add 명령어에 x, y, z 또는 playerName으로 위치 설정 가능

-예외처리 잘 안되있음

#YML
list.yml

```
#name:
  block: #
  power: #
  period: #
  blockLifeTime: #
  x: #
  y: #
  z: #
  taskId: #
  state: #
  owner: #
```
