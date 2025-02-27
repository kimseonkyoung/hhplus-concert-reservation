import http from 'k6/http';
import { check, sleep } from 'k6';
import { Trend, Rate } from 'k6/metrics';

// 성능 측정을 위한 메트릭 정의
export let responseTime = new Trend('response_time');
export let successRate = new Rate('success_rate');

export let options = {
    stages: [
        {  duration: '1m', target: 50 },    // 1분 동안 50명
        { duration: '1m', target: 50 },   // 1분 동안 500명
        { duration: '1m', target: 200 },  // 1분 동안 2000명
        { duration: '1m', target: 500 },  // 1분 동안 5000명
    ],
    thresholds: {
        http_req_duration: ['p(95)<500'],  // 95%의 요청이 500ms 이하
        success_rate: ['rate>0.95'],       // 성공률 95% 이상 유지
    },
};

const BASE_URL = 'http://localhost:8080/api/user/balance/charge'; // API 주소 (변경 가능)

// 무작위 유저 데이터 생성
function generateUserData() {
    return {
        userId: Math.floor(Math.random() * 1000000) + 1, // 1 ~ 1,000,000 랜덤 유저
        amount: Math.floor(Math.random() * 50000) + 1000, // 1,000 ~ 50,000 랜덤 충전
    };
}

export default function () {
    let payload = JSON.stringify(generateUserData());
    let params = {
        headers: { 'Content-Type': 'application/json' },
    };

    let res = http.post(BASE_URL, payload, params);

    // 응답 시간 측정
    responseTime.add(res.timings.duration);

    // 성공 여부 체크
    let success = check(res, {
        'is status 200': (r) => r.status === 200,
    });

    successRate.add(success);

    sleep(0.5); // 0.5초 대기 (조절 가능)
}
