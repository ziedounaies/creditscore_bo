
import { getEntity, deleteEntity } from './contact.reducer';



import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Button, Modal } from 'antd';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
export const ContactDeleteDialog = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();
  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const contactEntity = useAppSelector(state => state.contact.entity);
  const updateSuccess = useAppSelector(state => state.memberUser.updateSuccess);

  const handleClose = () => {
    navigate('/dashboard/contact');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(contactEntity.id));
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
        <Button key="delete" type="primary" danger onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Supprimer
        </Button>,
      ]}
    >
      <p>Êtes-vous certain de vouloir supprimer le contact {contactEntity.id} ?</p>
    </Modal>
  );
};

export default ContactDeleteDialog;
