import dayjs from 'dayjs/esm';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { Type } from 'app/entities/enumerations/type.model';

export interface INotification {
  id: number;
  title?: string | null;
  message?: string | null;
  sentAt?: dayjs.Dayjs | null;
  type?: keyof typeof Type | null;
  user?: Pick<IAppUser, 'id'> | null;
}

export type NewNotification = Omit<INotification, 'id'> & { id: null };
