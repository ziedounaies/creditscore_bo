import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import {  ModalHeader, ModalBody, ModalFooter } from 'reactstrap';

import { Button, Modal } from 'antd';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './agencies.reducer';

export const AgenciesDeleteDialog = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();
  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const agenciesEntity = useAppSelector(state => state.agencies.entity);
  const updateSuccess = useAppSelector(state => state.memberUser.updateSuccess);

  const handleClose = () => {
    navigate('/dashboard/agences');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(agenciesEntity.id));
  };

  const [visible, setVisible] = useState(true); // Change to true if you want the modal to be initially visible
  const [confirmLoading, setConfirmLoading] = useState(false);
  const [modalText, setModalText] = useState('Content of the modal');

  const handleOk = () => {
    setModalText('The modal will be closed after two seconds');
    setConfirmLoading(true);
    setTimeout(() => {
      setVisible(false);
      setConfirmLoading(false);
    }, 2000);
  };

  const handleCancel = () => {
    console.log('Clicked cancel button');
    setVisible(false);
  };


  return (


    <Modal
      title="Confirmation de suppression"
      visible={visible} // Use visible instead of open
      onOk={handleOk}
      confirmLoading={confirmLoading}
      onCancel={handleCancel}
      footer={[
        <Button key="cancel" onClick={handleCancel}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Annuler
        </Button>,
        <Button id="jhi-confirm-delete-address" type="primary" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Supprimer
        </Button>,
      ]}
    >
      <p>Êtes-vous certain de vouloir supprimer l'agence {agenciesEntity.id} ?</p>
    </Modal>





/*
  <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="agenciesDeleteDialogHeading">
        Confirmation de suppression
      </ModalHeader>
      <ModalBody id="creditScoreApp.agencies.delete.question">
        Êtes-vous certain de vouloir supprimer le Agencies {agenciesEntity.id} ?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Annuler
        </Button>
        <Button id="jhi-confirm-delete-agencies" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Supprimer
        </Button>
      </ModalFooter>
    </Modal>*/
  );
};

export default AgenciesDeleteDialog;
