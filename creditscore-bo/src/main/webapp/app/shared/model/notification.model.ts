import dayjs from 'dayjs';
import { IMemberUser } from 'app/shared/model/member-user.model';

export interface INotification {
  id?: number;
  title?: string | null;
  message?: string | null;
  readed?: boolean | null;
  createdAt?: dayjs.Dayjs | null;
  memberUser?: IMemberUser | null;
}

export const defaultValue: Readonly<INotification> = {
  readed: false,
};
