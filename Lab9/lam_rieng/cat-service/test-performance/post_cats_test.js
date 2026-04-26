import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '10s', target: 50 },
        { duration: '20s', target: 150 },
        { duration: '30s', target: 400 },
        { duration: '10s', target: 0 },
    ],
    thresholds: {
        http_req_duration: ['p(95)<1000'],    // 95% request phải < 1s
        http_req_failed: ['rate<0.05'],       // lỗi < 5%
    },
};

export default function () {
  const url = 'http://localhost:3000/cats';
  const payload = JSON.stringify({
    name: `Mèo May Mắn ${Math.random().toString(36).substring(2, 6)}`,
    age: Math.floor(Math.random() * 15) + 1,
    breed: "Mèo mướp",
  });

  const params = { headers: { 'Content-Type': 'application/json' } };
  const res = http.post(url, payload, params);

  check(res, {
    'status is 202': (r) => r.status === 202,
    'response time < 1000ms': (r) => r.timings.duration < 1000,
  });

  sleep(Math.random() + 0.5);
}
