import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Contact from './contact';
import ContactDetail from './contact-detail';
import ContactUpdate from './contact-update';
import ContactDeleteDialog from './contact-delete-dialog';

const ContactRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Contact />} />
    <Route path="new" element={<ContactUpdate />} />
    <Route path=":id">
      <Route index element={<ContactDetail />} />
      <Route path="edit" element={<ContactUpdate />} />
      <Route path="delete" element={<ContactDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ContactRoutes;
