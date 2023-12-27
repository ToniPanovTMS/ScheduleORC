#!/bin/bash
/bin/mysql -u root -pVyiu28giOCd9 <<EOF
USE asterisk
DELETE FROM queues_details WHERE data = 'Local/104@from-queue/n,0' AND id = '1729'
EOF
/bin/mysql -u root -pVyiu28giOCd9 <<EOF
USE asterisk
DELETE FROM queues_details WHERE data = 'Local/193@from-queue/n,0' AND id = '1729'
EOF
/bin/mysql -u root -pVyiu28giOCd9 <<EOF
USE asterisk
DELETE FROM queues_details WHERE data = 'Local/270@from-queue/n,0' AND id = '1729'
EOF
/bin/mysql -u root -pVyiu28giOCd9 <<EOF
USE asterisk
INSERT INTO queues_details (id, keyword, data, flags) VALUES ('1729', 'member', 'Local/181@from-queue/n,0', '0')
EOF
/bin/mysql -u root -pVyiu28giOCd9 <<EOF
USE asterisk
INSERT INTO queues_details (id, keyword, data, flags) VALUES ('1729', 'member', 'Local/180@from-queue/n,0', '0')
EOF
/bin/mysql -u root -pVyiu28giOCd9 <<EOF
USE asterisk
INSERT INTO queues_details (id, keyword, data, flags) VALUES ('1729', 'member', 'Local/270@from-queue/n,0', '0')
EOF
/bin/mysql -u root -pVyiu28giOCd9 <<EOF
USE asterisk
INSERT INTO queues_details (id, keyword, data, flags) VALUES ('1729', 'member', 'Local/108@from-queue/n,0', '0')
EOF
/var/lib/asterisk/bin/amportal a r