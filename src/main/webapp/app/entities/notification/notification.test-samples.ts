import dayjs from 'dayjs/esm';

import { INotification, NewNotification } from './notification.model';

export const sampleWithRequiredData: INotification = {
  id: 10110,
  title: 'ramper triathlète',
};

export const sampleWithPartialData: INotification = {
  id: 19694,
  title: 'dring',
  type: 'INFO',
};

export const sampleWithFullData: INotification = {
  id: 5787,
  title: 'sédentaire émérite',
  message: '../fake-data/blob/hipster.txt',
  sentAt: dayjs('2026-05-12T10:06'),
  type: 'WARNING',
};

export const sampleWithNewData: NewNotification = {
  title: 'de peur que en vérité coin-coin',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
