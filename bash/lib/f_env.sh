## Provide environment-specific variable definitions
##
## USAGE:
## process_environment "stage"
##

COLOR_FAIL=$'\033[1;31m'
COLOR_RESET=$'\033[0m'

function process_environment()
{
	export __ENV="$1"

	if [ -z "${__ENV}" ]; then
		local msg="%sERROR:%s process_environment() requires an environment argument. None provided.\n"
		printf "$msg" "$COLOR_FAIL" "$COLOR_RESET"
		exit 1
	fi

	case "${__ENV}" in
		dev)
			export __ZOOKEEPER="localhost:2181/kafka"
			export __KAFKA_BROKER="localhost:9092"
			;;
		qa)
			export __ZOOKEEPER="qa.foo.com:2181/kafka"
			export __KAFKA_BROKER="localhost:9092"
			;;
		stage)
			export __ZOOKEEPER="stage.foo.com:2181/kafka"
			export __KAFKA_BROKER="localhost:9092"
			;;
		prod)
			export __ZOOKEEPER="prod.foo.com:2181/kafka"
			export __KAFKA_BROKER="localhost:9092"
			;;
		*)
			local msg="%sERROR%s process_environment(): Environment required. Value [%s] not recognized."
			printf "$msg" "$COLOR_FAIL" "$COLOR_RESET" "${__ENV}"
			echo "Valid choices are [dev, qa, stage, prod]"
	esac
			
}
		

