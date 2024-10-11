import React, { useEffect, useState } from 'react';
import { ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import {Button, Card, Divider, Form, FormProps, Input, message, Typography} from 'antd';
import {Link} from "react-router-dom";
import {useAppSelector} from "app/config/store";
//import Title from 'antd/es/skeleton/Title';
import { getLoginUrl, REDIRECT_URL } from 'app/shared/util/url-utils';
const { Title } = Typography;

export const ResetPassword = () => {
  const [showAlert, setShowAlert] = useState(true);

  const account = useAppSelector(state => state.authentication.account);
  useEffect(() => {
    if (account?.login) {
      message.info(`Vous êtes connecté en tant que "${account.login}".`);
    }
  }, [account]); // Trigger effect when account changes

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      setShowAlert(false);
    }, 4000);
    return () => clearTimeout(timeoutId);
  }, []);

  const handleValidSubmit = ({ email }) => {
    // handle form submission
  };
  type FieldType = {
    username?: string;
    password?: string;
    remember?: string;
  };

  const onFinish: FormProps<FieldType>["onFinish"] = (values) => {
    console.log('Success:', values);
  };

  const onFinishFailed: FormProps<FieldType>["onFinishFailed"] = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };
  return (
    <div
      style={{
        display: 'flex',
        justifyContent: 'space-between', // Adjust space between items
        alignItems: 'center',
        height: '100vh',
        fontFamily: 'Segoe UI',
        padding: '0 50px', // Add padding for the sides
      }}
    >
      {/* Empty space */}
      <div style={{width: '50%'}}>
        <img src="content/images/3.gif" alt="Logo" width='120%' height='100%' style={{marginLeft:-200}}/>
      </div>

      {/* Content */}
      <div style={{width: '50%', display: 'flex', flexDirection: 'column' }}>



        <Card
          style={{
            display:'flex',
            justifyContent:'center',
            alignItems:'center',
            borderRadius: 10,
            width: '100%',
            height: 450,
            boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
          }}
        >
          <div style={{marginBottom:40}}>
            <Title style={{marginBottom:-15}} level={3}>SureScoreTunis :</Title>
            <Title style={{marginBottom:-15}}  level={3}>Votre passerelle </Title>
            <Title level={3}>confiance et stabilité financière.</Title>
          </div>
          <Form
            name="basic"
            labelCol={{span: 8}}
            wrapperCol={{span: 16}}
            initialValues={{remember: true}}
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            autoComplete="off"
          >
            <div style={{marginBottom: 10}}>Username</div>
            <Form.Item

              name="username"
              rules={[{required: true, message: 'Please input your username!'}]}
              style={{marginBottom: 16}} // Add bottom margin for spacing
            >
              <Input style={{width:350}} placeholder="Username"/>
            </Form.Item>



            {/* Additional input fields can be added here */}

            <Form.Item style={{marginBottom: 16}}> {/* Add bottom margin for spacing */}

              <Button  style={{width:350}}  type="primary" htmlType="submit">
                Submit
              </Button>
            </Form.Item>
          </Form>

        </Card>
      </div>
    </div>
  );
};

export default ResetPassword;





