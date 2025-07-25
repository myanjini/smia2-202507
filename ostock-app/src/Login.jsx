import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
    const [id, setId] = useState('');
    const [pw, setPw] = useState('');

    const onChangeId = e => setId(e.target.value);
    const onChangePw = e => setPw(e.target.value);

    const navigate =useNavigate();

    const onSubmit = e => {
        e.preventDefault();

        axios.post(
            "http://localhost:8080/login", 
            {memberId: id, memberPassword: pw}
        )
        .then(res => {
            // 로그인에 성공하면 토큰을 추출해서 세션 스토리지에 저장하고, 
            // 라이센스 컴포넌트로 이동
            if (res.status === 200) {
                const token = res.headers["token"];
                sessionStorage.setItem("token", token);
                sessionStorage.setItem("memberid", id);
                navigate("/license");
            }
        })
        .catch(err => console.log(err));
    };

    return (
        <>
            <h1>로그인</h1>

            <form onSubmit={onSubmit}>
                ID: <input type="text" value={id} onChange={onChangeId} />
                <br/>
                PW: <input type="password" value={pw} onChange={onChangePw} />
                <br/>
                <button type="submit">로그인</button>
            </form>
        </>
    );
}
export default Login;