import { message } from 'antd';
import { isFulfilledAction, isRejectedAction } from 'app/shared/reducers/reducer.utils';
import { AxiosError, AxiosHeaderValue } from 'axios';

const addErrorAlert = (errorMsg: string, key?: string, data?: any) => {
  message.error(errorMsg); // Display error message using Ant Design's message component
};

export default () => next => action => {
  const { error, payload } = action;

  if (isFulfilledAction(action) && payload && payload.headers) {
    const headers = payload?.headers;
    let alert: string | null = null;
    headers &&
    Object.entries<string>(headers).forEach(([k, v]) => {
      if (k.toLowerCase().endsWith('app-alert')) {
        alert = v;
      }
    });
    if (alert) {
      message.success(
        "Action effectuée avec succès !"
      ); // Display success message using Ant Design's message component
    }
  }

  if (isRejectedAction(action) && error && error.isAxiosError) {
    const axiosError = error as AxiosError;
    if (axiosError.response) {
      const response = axiosError.response;
      const data = response.data as any;
      if (
        !(response.status === 401 && (axiosError.message === '' || response.config.url === 'api/account' || response.config.url === 'api/authenticate'))
      ) {
        switch (response.status) {
          case 0:
            addErrorAlert('Server not reachable', 'error.server.not.reachable');
            break;

          case 400:
            // Handle error cases and display messages using Ant Design's message component
            // Example: addErrorAlert('Error message here');
            break;

          case 404:
            addErrorAlert('Not found', 'error.url.not.found');
            break;

          default:
            addErrorAlert(data?.message || data?.error || data?.title || 'Unknown error!');
        }
      }
    } else if (axiosError.config && axiosError.config.url === 'api/account' && axiosError.config.method === 'get') {
      console.log('Authentication Error: Trying to access url api/account with GET.');
    } else {
      addErrorAlert(error.message || 'Unknown error!');
    }
  } else if (error) {
    addErrorAlert(error.message || 'Unknown error!');
  }

  return next(action);
};
