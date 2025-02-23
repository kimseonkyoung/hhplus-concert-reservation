import http from 'k6/http';
import { check } from 'k6';

// 📌 단일 요청 옵션 (1회 실행)
export let options = {
    vus: 1,
    iterations: 1,
};

// 📌 단일 요청 실행
export default function () {
    let resetUrl = `http://localhost:8080/api/token/reset`;
    let createUrl = `http://localhost:8080/api/token/create?userId=${Math.floor(Math.random() * 10000)}`;

    // 1️⃣ 기존 토큰 삭제
    let resetRes = http.del(resetUrl);
    check(resetRes, { 'Token Reset Successful': (r) => r.status === 200 });

    // 2️⃣ 새로운 토큰 생성
    let res = http.get(createUrl);
    check(res, { 'Token Created': (r) => r.status === 200 });
}
