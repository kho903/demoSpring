
INSERT INTO QNA (id, category, phone_number, email, title, message, checked, create_date)
VALUES (1, '제휴문의', '010-5013-6138', 'kho903@gmail.com',
        '제 브랜드와 제휴하고 싶습니다.',
        '뱅카우 측과 저희 브랜드가 만난다면 좋은 시너지를 발휘할 것입니다.',
        true, '2022-01-01 00:02:00.000000');

INSERT INTO MAIL_TEMPLATE(ID, TEMPLATE_ID, TITLE, CONTENTS, SEND_EMAIL, SEND_USER_NAME, REG_DATE)
VALUES
(1, 'ADMIN_REGISTER', '{USER_NAME} 님의 회원가입 인증 이메일입니다.',
 '<div><p>{USER_NAME} 님 안녕하세요.</p><p>아래 링크를 클릭하여 인증을 완료하여 주세요.</p><p><a href="{SERVER_URL}/reset?key={USER_AUTHENTICATION_KEY}">인증하기</a></p></div>',
 'test@naver.com', '관리자', '2022-01-01 00:00:00.000000');

