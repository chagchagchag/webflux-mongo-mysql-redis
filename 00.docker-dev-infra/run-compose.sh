cnt_example_network=`docker network ls --filter name=example-network | wc -l`
cnt_example_network=$(($cnt_example_network -1))

if [ $cnt_example_network -eq 0 ]
then
  echo "'example-network' 가 존재하지 않습니다. 'example-network' 를 새로 생성합니다."
  docker network create example-network
fi

docker-compose -f common.yml -f docker-compose.yml up -d