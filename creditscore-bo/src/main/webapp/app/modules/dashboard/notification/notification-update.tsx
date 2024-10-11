import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import {Row, Col, Form, Input, Button, Card, Divider, Typography, Select} from 'antd';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime} from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, updateEntity, createEntity, reset } from './notification.reducer';
import { getEntities as getMemberUsers } from 'app/entities/member-user/member-user.reducer';
import {
  CalendarOutlined,
  CheckCircleOutlined,
  FontColorsOutlined,
  MessageOutlined,
  UserOutlined
} from "@ant-design/icons";
import { Checkbox } from 'antd';
import type { CheckboxProps } from 'antd';
import {StatusType} from "app/shared/model/enumerations/status-type.model";

const { Title,Text } = Typography;

const NotificationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const memberUsers = useAppSelector(state => state.memberUser.entities);
  const notificationEntity = useAppSelector(state => state.notification.entity);
  const loading = useAppSelector(state => state.notification.loading);
  const updating = useAppSelector(state => state.notification.updating);
  const updateSuccess = useAppSelector(state => state.notification.updateSuccess);

  const [form] = Form.useForm();

  const handleClose = () => {
    navigate('/dashboard/notification' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMemberUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const isEnabled = values.readed; // true for "Actif", false for "Inactif"

    const entity = {
      ...notificationEntity,
      ...values,
      enabled: isEnabled,
      memberUser: memberUsers.find(it => it.id.toString() === values.memberUser.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
        createdAt: displayDefaultDateTime(),
      }
      : {
        ...notificationEntity,
        createdAt: convertDateTimeFromServer(notificationEntity.createdAt),
        memberUser: notificationEntity?.memberUser?.id,
      };

  useEffect(() => {
    isNew
      ? form.setFieldsValue({
        createdAt: displayDefaultDateTime(),
      })
      : form.setFieldsValue({
        ...notificationEntity,
        createdAt: convertDateTimeFromServer(notificationEntity.createdAt),
        memberUser: notificationEntity?.memberUser?.id,
      });
  }, [notificationEntity]);

  return (
    <div>
      <Card style={{ borderRadius: 10, width: '100%',height:690 }}>
        <Row justify="space-between" style={{ marginBottom: 15 }}>
          <Col>

            <Title level={2} style={{ marginBottom: 0 }}>
              {isNew ? `Créer une notification` : `Fiche notification`}
            </Title>
            <Text type="secondary">
              Réclamations - Exprimez Vos Préoccupations
            </Text>
          </Col>
          <Col>
            <div style={{ display: 'flex', flexDirection: 'row' }}>
              <Link to="/dashboard/notification">
                <Button className="me-2" style={{ borderRadius: 10, backgroundColor: 'white', color: '#333' }}>
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Retour</span>
                </Button>
              </Link>
              <Button
                type="primary"
                style={{ borderRadius: 10, backgroundColor: '#FF3029' }}
                htmlType="submit"
                form="notificationForm"
              >
                <FontAwesomeIcon icon="save" />
                &nbsp; Sauvegarder
              </Button>
            </div>
          </Col>
        </Row>
        <Divider />
        <Row>
          <Col span={11}>
            <Form
              layout="vertical"
              name="notificationForm"
              onFinish={saveEntity}
              initialValues={defaultValues()}
              // Ensure correct usage of spread operator here
              {...{ onFinish: saveEntity, initialValues: defaultValues() }}
              form={form}
            >

              <Form.Item
                label="Titre"
                name="title"
                rules={[{message: 'Veuillez saisir titre!'}]}
              >
                <Input
                  prefix={<FontColorsOutlined className="site-form-item-icon"/>}

                  placeholder="titre"
                />
              </Form.Item>


              <Form.Item
                label="Message"
                name="message"
                rules={[{message: 'Veuillez saisir message!'}]}
              >
                <Input
                  prefix={<MessageOutlined className="site-form-item-icon"/>}

                  placeholder="message"
                />
              </Form.Item>

              <Form.Item

                id="notification-readed" name="readed" data-cy="readed"


                label="Lu/Non lu"

                rules={[{ required:true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Select style={{ width: 600 }}>
                  <Select.Option value={true}>Lu</Select.Option>
                  <Select.Option value={false}>Non lu</Select.Option>
                </Select>
              </Form.Item>

              <Form.Item
                id="address-memberUser"
                label="user"
                name="memberUser"
                rules={[{ required:true,message: 'Veuillez renseigner ce champ !' }]}
              >
                <Select

                >
                  <option value="" key="" />
                  {memberUsers
                    ? memberUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.userName}
                      </option>
                    ))
                    : null}    </Select>
              </Form.Item>





            </Form>
          </Col>
          <Divider type="vertical" style={{ height: 525 }} />
          <Col span={12} >
            {/* Additional content for the right half of the page */}
          </Col>
        </Row>
      </Card>
    </div>
  );
};

export default NotificationUpdate;
