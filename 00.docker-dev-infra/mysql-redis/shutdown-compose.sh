rm -rf volumes
docker-compose -f common.yml -f docker-compose.yml down

cnt_example_network=`docker network ls --filter name=example-network | wc -l`
cnt_example_network=$(($cnt_example_network -1))

if [ $cnt_example_network -ne 0 ]
then
  echo "'example-network' 가 존재합니다. 'example-network' 를 삭제합니다."
  docker network rm example-network
fi