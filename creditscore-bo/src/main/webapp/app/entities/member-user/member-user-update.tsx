import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICreditRapport } from 'app/shared/model/credit-rapport.model';
import { getEntities as getCreditRapports } from 'app/entities/credit-rapport/credit-rapport.reducer';
import { IInvoice } from 'app/shared/model/invoice.model';
import { getEntities as getInvoices } from 'app/entities/invoice/invoice.reducer';
import { IMemberUser } from 'app/shared/model/member-user.model';
import { AcountType } from 'app/shared/model/enumerations/acount-type.model';
import { IdentifierType } from 'app/shared/model/enumerations/identifier-type.model';
import { Role } from 'app/shared/model/enumerations/role.model';
import { getEntity, updateEntity, createEntity, reset } from './member-user.reducer';

export const MemberUserUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const creditRapports = useAppSelector(state => state.creditRapport.entities);
  const invoices = useAppSelector(state => state.invoice.entities);
  const memberUserEntity = useAppSelector(state => state.memberUser.entity);
  const loading = useAppSelector(state => state.memberUser.loading);
  const updating = useAppSelector(state => state.memberUser.updating);
  const updateSuccess = useAppSelector(state => state.memberUser.updateSuccess);
  const acountTypeValues = Object.keys(AcountType);
  const identifierTypeValues = Object.keys(IdentifierType);
  const roleValues = Object.keys(Role);

  const handleClose = () => {
    navigate('/member-user' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCreditRapports({}));
    dispatch(getInvoices({}));
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
    values.birthDate = convertDateTimeToServer(values.birthDate);
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...memberUserEntity,
      ...values,
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
          birthDate: displayDefaultDateTime(),
          createdAt: displayDefaultDateTime(),
        }
      : {
          acountType: 'PHYSICAL_PERSON',
          identifierType: 'CIN',
          role: 'CUSTOMER',
          ...memberUserEntity,
          birthDate: convertDateTimeFromServer(memberUserEntity.birthDate),
          createdAt: convertDateTimeFromServer(memberUserEntity.createdAt),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="creditScoreApp.memberUser.home.createOrEditLabel" data-cy="MemberUserCreateUpdateHeading">
            Créer ou éditer un Member User
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="member-user-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="User Name" id="member-user-userName" name="userName" data-cy="userName" type="text" />
              <ValidatedField label="First Name" id="member-user-firstName" name="firstName" data-cy="firstName" type="text" />
              <ValidatedField label="Last Name" id="member-user-lastName" name="lastName" data-cy="lastName" type="text" />
              <ValidatedField label="Business Name" id="member-user-businessName" name="businessName" data-cy="businessName" type="text" />
              <ValidatedField
                label="Birth Date"
                id="member-user-birthDate"
                name="birthDate"
                data-cy="birthDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Acount Type" id="member-user-acountType" name="acountType" data-cy="acountType" type="select">
                {acountTypeValues.map(acountType => (
                  <option value={acountType} key={acountType}>
                    {acountType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Identifier Type"
                id="member-user-identifierType"
                name="identifierType"
                data-cy="identifierType"
                type="select"
              >
                {identifierTypeValues.map(identifierType => (
                  <option value={identifierType} key={identifierType}>
                    {identifierType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Identifier Value"
                id="member-user-identifierValue"
                name="identifierValue"
                data-cy="identifierValue"
                type="text"
              />
              <ValidatedField
                label="Employers Reported"
                id="member-user-employersReported"
                name="employersReported"
                data-cy="employersReported"
                type="text"
              />
              <ValidatedField label="Income" id="member-user-income" name="income" data-cy="income" type="text" />
              <ValidatedField label="Expenses" id="member-user-expenses" name="expenses" data-cy="expenses" type="text" />
              <ValidatedField label="Gross Profit" id="member-user-grossProfit" name="grossProfit" data-cy="grossProfit" type="text" />
              <ValidatedField
                label="Net Profit Margin"
                id="member-user-netProfitMargin"
                name="netProfitMargin"
                data-cy="netProfitMargin"
                type="text"
              />
              <ValidatedField
                label="Debts Obligations"
                id="member-user-debtsObligations"
                name="debtsObligations"
                data-cy="debtsObligations"
                type="text"
              />
              <ValidatedField label="Enabled" id="member-user-enabled" name="enabled" data-cy="enabled" check type="checkbox" />
              <ValidatedField label="Role" id="member-user-role" name="role" data-cy="role" type="select">
                {roleValues.map(role => (
                  <option value={role} key={role}>
                    {role}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Created At"
                id="member-user-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/member-user" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Retour</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Sauvegarder
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MemberUserUpdate;
