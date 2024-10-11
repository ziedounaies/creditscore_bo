import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import {Row, Col, FormText, Card} from 'reactstrap';
import {

  Button,

} from 'antd';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICreditRapport } from 'app/shared/model/credit-rapport.model';
import { getEntities as getCreditRapports } from 'app/entities/credit-rapport/credit-rapport.reducer';
import { IMemberUser } from 'app/shared/model/member-user.model';
import { Role } from 'app/shared/model/enumerations/role.model';
import { TypeContact } from 'app/shared/model/enumerations/type-contact.model';
import { getEntity, updateEntity, createEntity, reset } from './member-user.reducer';

export const MemberUserUpdate = () => {

  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const creditRapports = useAppSelector(state => state.creditRapport.entities);
  const memberUserEntity = useAppSelector(state => state.memberUser.entity);
  const loading = useAppSelector(state => state.memberUser.loading);
  const updating = useAppSelector(state => state.memberUser.updating);
  const updateSuccess = useAppSelector(state => state.memberUser.updateSuccess);
  const roleValues = Object.keys(Role);
  const typeContactValues = Object.keys(TypeContact);

  const handleClose = () => {
    navigate('/member-user');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCreditRapports({}));
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
    values.dateBirth = convertDateTimeToServer(values.dateBirth);
    if (values.document !== undefined && typeof values.document !== 'number') {
      values.document = Number(values.document);
    }
    if (values.creditScore !== undefined && typeof values.creditScore !== 'number') {
      values.creditScore = Number(values.creditScore);
    }
    if (values.employersReported !== undefined && typeof values.employersReported !== 'number') {
      values.employersReported = Number(values.employersReported);
    }

    const entity = {
      ...memberUserEntity,
      ...values,
      memberUser: creditRapports.find(it => it.id.toString() === values.memberUser.toString()),
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
        dateBirth: displayDefaultDateTime(),
      }
      : {
        role: 'CUSTOMER',
        typeContact: 'PHONENUMBER',
        ...memberUserEntity,
        dateBirth: convertDateTimeFromServer(memberUserEntity.dateBirth),
        memberUser: memberUserEntity?.memberUser?.id,
      };

  return (
    <div style={{display:'flex',flexDirection:'row',justifyContent:'center'}}>
      <Card style={{borderRadius:10,width:"55%"}}>
        <Row className="justify-content-center">
          <div style={{marginBottom:15}}>
            <h2  id="creditScoreApp.memberUser.home.createOrEditLabel" data-cy="MemberUserCreateUpdateHeading">
              Member User
            </h2>
          </div>
        </Row>
        <Row style={{width:'150%'}} >
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
                {!isNew ? <ValidatedField  style={{borderRadius: 10}} name="id" required readOnly id="member-user-id" label="ID" validate={{ required: true }} /> : null}
                <ValidatedField style={{borderRadius: 10}}
                                label="Date de naissance"
                                id="member-user-dateBirth"
                                name="dateBirth"
                                data-cy="dateBirth"
                                type="datetime-local"
                                placeholder="YYYY-MM-DD HH:mm"
                />
                <ValidatedField style={{borderRadius: 10}} label="Document" id="member-user-document" name="document" data-cy="document" type="text" />
                <ValidatedField  style={{borderRadius: 10}} label="Credit Score" id="member-user-creditScore" name="creditScore" data-cy="creditScore" type="text" />
                <ValidatedField style={{borderRadius: 10}}
                                label="Statut d'emploi"
                                id="member-user-employersReported"
                                name="employersReported"
                                data-cy="employersReported"
                                type="text"
                />
                <ValidatedField style={{borderRadius: 10}} label="Role" id="member-user-role" name="role" data-cy="role" type="select">
                  {roleValues.map(role => (
                    <option value={role} key={role}>
                      {role}
                    </option>
                  ))}
                </ValidatedField>
                <ValidatedField style={{borderRadius: 10}} label="Type Contact" id="member-user-typeContact" name="typeContact" data-cy="typeContact" type="select">
                  {typeContactValues.map(typeContact => (
                    <option value={typeContact} key={typeContact}>
                      {typeContact}
                    </option>
                  ))}
                </ValidatedField>
                <ValidatedField  style={{borderRadius: 10}} id="member-user-memberUser" name="memberUser" data-cy="memberUser" label="Member User" type="select">
                  <option value="" key="0" />
                  {creditRapports
                    ? creditRapports.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                    : null}
                </ValidatedField>
                <Link to="/dashboard/customers">
                  <Button className="me-2" style={{borderRadius: 10, backgroundColor: 'white', color: '#333'}}
                          id="cancel-save" data-cy="entityCreateCancelButton">
                    <FontAwesomeIcon icon="arrow-left"/>
                    &nbsp;
                    <span className="d-none d-md-inline">Retour</span>
                  </Button>
                </Link>
                &nbsp;
                <Button type="primary" style={{borderRadius: 10, backgroundColor: "#FF3029"}} id="save-entity"
                        data-cy="entityCreateSaveButton" htmlType="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save"/>
                  &nbsp;   Sauvegarder
                </Button>

                {/*   <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/member-user" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Retour</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Sauvegarder
              </Button>*/}

              </ValidatedForm>
            )}
          </Col>
        </Row>
      </Card>

    </div>
  );
};

export default MemberUserUpdate;
