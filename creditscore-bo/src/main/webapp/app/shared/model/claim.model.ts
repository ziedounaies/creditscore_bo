import dayjs from 'dayjs';
import { IMemberUser } from 'app/shared/model/member-user.model';
import { StatusType } from 'app/shared/model/enumerations/status-type.model';

export interface IClaim {
  id?: number;
  status?: keyof typeof StatusType | null;
  title?: string | null;
  message?: string | null;
  createdAt?: dayjs.Dayjs | null;
  memberUser?: IMemberUser | null;
}

export const defaultValue: Readonly<IClaim> = {};
