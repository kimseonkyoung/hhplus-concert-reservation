import http from 'k6/http';
import { check, sleep, Trend } from 'k6';

// 📌 점진적 부하 테스트 옵션 설정
export let options = {
    stages: [
        { duration: '10s', target: 1000 },   // 10초 동안 2000명 동시 요청
        { duration: '20s', target: 500 },   // 20초 동안 1000명 동시 요청
        { duration: '30s', target: 200 },    // 30초 동안 500명 동시 요청
        { duration: '40s', target: 100 },    // 40초 동안 200명 동시 요청
        { duration: '10s', target: 50 }     // 50초 동안 100명 동시 요청
    ]
};

// 📌 다수 요청 테스트 실행
export default function () {

    let userId = Math.floor(Math.random() * 10000);
    let url = `http://localhost:8080/api/token/create?userId=${userId}`;

    let res = http.get(url);

    check(res, { 'is status 200': (r) => r.status === 200 });

    sleep(1);
}
