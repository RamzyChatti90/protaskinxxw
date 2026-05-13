import { IAppUser, NewAppUser } from './app-user.model';

export const sampleWithRequiredData: IAppUser = {
  id: 19407,
  firstName: 'Léopoldine',
  lastName: 'Arnaud',
};

export const sampleWithPartialData: IAppUser = {
  id: 7641,
  firstName: 'Lucien',
  lastName: 'Gaillard',
  avatarUrl: 'https:r,g_{#',
  phone: '34774 ',
};

export const sampleWithFullData: IAppUser = {
  id: 1902,
  firstName: 'Amalric',
  lastName: 'Marty',
  avatarUrl: 'https:41EF',
  bio: '../fake-data/blob/hipster.txt',
  phone: '+554',
};

export const sampleWithNewData: NewAppUser = {
  firstName: 'Noé',
  lastName: 'Berger',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
