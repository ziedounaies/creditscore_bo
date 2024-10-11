import React from 'react';
import { ValidatedField } from 'react-jhipster';
import {Button, Modal, ModalHeader, ModalBody, ModalFooter, Alert, Row, Col, Form, Card} from 'reactstrap';
import { Link } from 'react-router-dom';
import { type FieldError, useForm } from 'react-hook-form';



const resetPasswordUser = () => {


  const {
    handleSubmit,
    register,
    formState: { errors, touchedFields },
  } = useForm({ mode: 'onTouched' });






  return (
    <div
      style={{width: "100vw", height: '100vh',flexDirection:'column',fontFamily:'Segoe UI',display:'flex'}}>


      <Card
        style={{
          borderRadius: 10,
          height: '12%',
          width: '100%',
          boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
        }}
      >
        <h2 style={{ fontFamily: 'serif', textAlign: 'left' }}>Reset password</h2>
      </Card>



      <div style={{display:'flex',flexDirection:'column',justifyContent:'center',alignItems:'center',marginTop:80,marginRight:350}} >

        <Card style={{

          borderRadius: 10,

          width: "40%",
          height: 400,

        }}>
          <Form
           >
            <div id="login-title" data-cy="loginTitle">
              <h1 style={{marginBottom: 10,fontFamily: 'serif'}}>Reset Password</h1>
            </div>
            <div style={{display:'flex',flexDirection:'column'}} >
              <div>
                <div >

                </div>
                <Col md="12">
                  <ValidatedField

                    name="username"
                    label="New password"
                    placeholder="Your username"
                    required
                    autoFocus
                    data-cy="username"
                    validate={{required: 'Username cannot be empty!'}}
                    register={register}
                    error={errors.username as FieldError}
                    isTouched={touchedFields.username}
                    style={{ borderRadius: 10}}
                  />
                  <ValidatedField

                    name="password"
                    type="password"
                    label="Confirm new password"
                    placeholder="Your password"
                    required
                    data-cy="password"
                    validate={{required: 'Password cannot be empty!'}}
                    register={register}
                    error={errors.password as FieldError}
                    isTouched={touchedFields.password}
                    style={{ borderRadius: 10}}

                  />

                </Col>
              </div>
              <div className="mt-1">&nbsp;</div>



              <div>


                {/*<Link to={"/home"} >     </Link>*/}
                <Button style={{backgroundColor: '#FF3029', borderRadius: 10,width: '100%'}} type="submit"
                        data-cy="submit">
                  Reset
                </Button>

              </div>
            </div>
          </Form>
        </Card>

      </div>
    </div>

  );
};

export default resetPasswordUser;
