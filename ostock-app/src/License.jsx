import axios from "axios";
import { useEffect, useState } from "react";

function License() {
    const [productName, setProductName] = useState('');
    const [licenseId, setLicenseId] = useState('');
    const [contactName, setContactName] = useState('');
    
    // 컴포넌트가 마운트(최초 렌더링된 후)되면 라이센스 조회 서비스를 호출
    useEffect(() => {
        const url = `http://localhost:8072/license-service/v1/organization/d898a142-de44-466c-8c88-9ceb2c2429d3/license/f2a9c9d4-d2c0-44fa-97fe-724d77173c62/rest`;
        axios
        .get(url, {
            headers: {
                'Authorization': `Bearer ${sessionStorage.getItem('token')}`, 
                'memberid': `${sessionStorage.getItem('memberid')}`
            }
        })
        .then(res => {
            console.log(res);
            setProductName(res.data.productName);
            setLicenseId(res.data.licenseId);
            setContactName(res.data.contactName);
        })
        .catch(err => console.log(err));
    }, []);

    return (
        <>
            <h1>라이센스</h1>

            <h2>{productName}</h2>
            <h2>{licenseId}</h2>
            <h2>{contactName}</h2>
        </>
    );
}
export default License;