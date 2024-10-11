import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMemberUser } from 'app/shared/model/member-user.model';
import { getEntities as getMemberUsers } from 'app/entities/member-user/member-user.reducer';
import { ICreditRapport } from 'app/shared/model/credit-rapport.model';
import { getEntities as getCreditRapports } from 'app/entities/credit-rapport/credit-rapport.reducer';
import { IInvoice } from 'app/shared/model/invoice.model';
import { StatusType } from 'app/shared/model/enumerations/status-type.model';
import { getEntity, updateEntity, createEntity, reset } from './invoice.reducer';

export const InvoiceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const memberUsers = useAppSelector(state => state.memberUser.entities);
  const creditRapports = useAppSelector(state => state.creditRapport.entities);
  const invoiceEntity = useAppSelector(state => state.invoice.entity);
  const loading = useAppSelector(state => state.invoice.loading);
  const updating = useAppSelector(state => state.invoice.updating);
  const updateSuccess = useAppSelector(state => state.invoice.updateSuccess);
  const statusTypeValues = Object.keys(StatusType);

  const handleClose = () => {
    navigate('/invoice' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMemberUsers({}));
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
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...invoiceEntity,
      ...values,
      memberUser: memberUsers.find(it => it.id.toString() === values.memberUser.toString()),
      creditRapport: creditRapports.find(it => it.id.toString() === values.creditRapport.toString()),
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
          status: 'PENDING',
          ...invoiceEntity,
          createdAt: convertDateTimeFromServer(invoiceEntity.createdAt),
          memberUser: invoiceEntity?.memberUser?.id,
          creditRapport: invoiceEntity?.creditRapport?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="creditScoreApp.invoice.home.createOrEditLabel" data-cy="InvoiceCreateUpdateHeading">
            Créer ou éditer un Invoice
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="invoice-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Invoice Number" id="invoice-invoiceNumber" name="invoiceNumber" data-cy="invoiceNumber" type="text" />
              <ValidatedField label="Amount" id="invoice-amount" name="amount" data-cy="amount" type="text" />
              <ValidatedField label="Status" id="invoice-status" name="status" data-cy="status" type="select">
                {statusTypeValues.map(statusType => (
                  <option value={statusType} key={statusType}>
                    {statusType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Created At"
                id="invoice-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="invoice-memberUser" name="memberUser" data-cy="memberUser" label="Member User" type="select">
                <option value="" key="0" />
                {memberUsers
                  ? memberUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="invoice-creditRapport" name="creditRapport" data-cy="creditRapport" label="Credit Rapport" type="select">
                <option value="" key="0" />
                {creditRapports
                  ? creditRapports.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/invoice" replace color="info">
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

export default InvoiceUpdate;
