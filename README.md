#Plugin
BlockFountain

#Minecraft version
1.12.2

#Author
worldbiomusic

#Version
1.0

#Description
-���м� �÷�����

-OP �ʿ�

-������ ��� ����

#Commands
<>: �ʼ�, 
[]: ����

```
/bf add <name> [ <block> <power> <period> <blockLifeTime> [<x> <y> <z> | <player>] ]
:���м� ����(������Ʈ)�� ���� 
power: x, y, z������ vector ũ��
period: �� ���� �ֱ� (��)
blockLifeTime: ���� ������ �������� ������ �ð� (��)
/bf start <name>
:���м� ����
/bf pause <name>
:���м� ��� ����
/bf remove <name>
:�� �м� ����
/bf list
:���м� ����Ʈ
/bf tp <name>
:���м��� ��ġ�� �̵�
/bf info <name>
: print [name, block, power, period, blockLifeTime, x, y, z taskId, state, owner]
:�� �м��� ���� ���� ���  
```

-bf add <name> ���� �����ϰ� �ڽ��� ��ġ�� ���м� ���� ����

-bf add ��ɾ x, y, z �Ǵ� playerName���� ��ġ ���� ����

-����ó�� �� �ȵ�����

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
