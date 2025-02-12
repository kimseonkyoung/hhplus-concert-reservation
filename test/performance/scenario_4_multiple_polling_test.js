import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = { vus: 1000, duration: '1m' };

export function setup() {
    let resetUrl = `http://localhost:8080/api/token/reset`;

    // 기존 토큰 삭제 (인터셉터 제외 경로)
    let resetRes = http.del(resetUrl);
    check(resetRes, { 'Token Reset Successful': (r) => r.status === 200 });

    let activeTokens = [];

    // 500개의 ACTIVE 토큰 생성
    for (let i = 0; i < 500; i++) {
        let res = http.get(`http://localhost:8080/api/token/create?userId=${i + 1}`);
        check(res, { [`Token ${i + 1} Created`]: (r) => r.status === 200 });

        let tokenUuid = res.json().tokenUuid;
        activeTokens.push(tokenUuid);
    }

    // 51번째 이후 사용자 (WAIT 상태 예상)
    let waitingTokens = [];
    for (let i = 51; i <= 100; i++) {  // 예시로 51~100번 사용자(50명) 대기
        let res = http.get(`http://localhost:8080/api/token/create?userId=${i}`);
        check(res, { [`Waiting Token ${i} Created`]: (r) => r.status === 200 });

        let tokenUuid = res.json().tokenUuid;
        waitingTokens.push(tokenUuid);
    }

    return { waitingTokens };
}

export default function (data) {
    let maxRetries = 2; // 최대 2번 재시도
    let interval = 5; // 5초 간격 폴링

    for (let tokenUuid of data.waitingTokens) {
        let headers = { 'x-token': tokenUuid };

        for (let i = 0; i < maxRetries; i++) {
            let res = http.get(`http://localhost:8080/api/concerts/list`, { headers });

            check(res, { 'Status is 200': (r) => r.status === 200 });

            let tokenStatus = res.json().status;

            if (tokenStatus === "ACTIVE") {
                console.log(`Token ${tokenUuid} is ACTIVE. Proceeding to concert list.`);

                // 콘서트 목록 요청 (인터셉터 적용됨 → x-token 필요)
                let concertRes = http.get(`http://localhost:8080/api/concerts/list`, { headers });
                check(concertRes, { 'Concert List Response is 200': (r) => r.status === 200 });
                break;
            } else if (tokenStatus === "WAIT") {
                console.log(`Token ${tokenUuid} is in WAIT status. Queue position: ${res.json().position}`);
                sleep(interval);
            } else {
                console.log(`Token ${tokenUuid} expired or invalid.`);
                break;
            }
        }
    }
}
