#!/bin/bash

# k6 테스트 스크립트 목록
TEST_SCRIPTS=(
    "scenario_1_concurrency.js"
    "scenario_2_queue.js"
    "scenario_3_large_data.js"
    "scenario_4_ttl.js"
    "scenario_5_cache_stampede.js"
)

# InfluxDB URL (Docker에서 실행 중)
INFLUXDB_URL="http://localhost:8086/k6"

# 로그 디렉토리 설정
LOG_DIR="./test/performance/logs"
mkdir -p $LOG_DIR
LOG_FILE="$LOG_DIR/k6-test-$(date +%F-%H%M%S).log"

echo "🚀 Running k6 Performance Tests..." | tee -a $LOG_FILE

# k6 스크립트 실행
for SCRIPT in "${TEST_SCRIPTS[@]}"; do
    echo "▶ Running: $SCRIPT" | tee -a $LOG_FILE
    k6 run --out influxdb=$INFLUXDB_URL "./test/performance/$SCRIPT" | tee -a $LOG_FILE
    echo "✅ Completed: $SCRIPT" | tee -a $LOG_FILE
done

echo "🎉 All tests completed! Logs saved in $LOG_FILE" | tee -a $LOG_FILE
