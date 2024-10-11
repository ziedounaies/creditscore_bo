import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MemberUser from './member-user';
import MemberUserDetail from './member-user-detail';
import MemberUserUpdate from './member-user-update';
import MemberUserDeleteDialog from './member-user-delete-dialog';

const MemberUserRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MemberUser />} />
    <Route path="new" element={<MemberUserUpdate />} />
    <Route path=":id">
      <Route index element={<MemberUserDetail />} />
      <Route path="edit" element={<MemberUserUpdate />} />
      <Route path="delete" element={<MemberUserDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MemberUserRoutes;
