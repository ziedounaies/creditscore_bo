import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Button, Modal } from 'antd';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './member-user.reducer';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const MemberUserDeleteDialog = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();
  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const memberUserEntity = useAppSelector(state => state.memberUser.entity);
  const updateSuccess = useAppSelector(state => state.memberUser.updateSuccess);

  const handleClose = () => {
    navigate('/dashboard/customers');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(memberUserEntity.id));
  };

  const [visible, setVisible] = useState(true);
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
    setVisible(false);
    handleClose(); // Navigate to /dashboard/customers when canceling
  };

  return (
    <Modal
      title="Confirmation de suppression"
      visible={visible}
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
      <p>Êtes-vous certain de vouloir supprimer le Member User {memberUserEntity.id} ?</p>
    </Modal>
  );
};

export default MemberUserDeleteDialog;
