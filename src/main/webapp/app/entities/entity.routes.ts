import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'protaskinxxwApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'app-user',
    data: { pageTitle: 'protaskinxxwApp.appUser.home.title' },
    loadChildren: () => import('./app-user/app-user.routes'),
  },
  {
    path: 'task',
    data: { pageTitle: 'protaskinxxwApp.task.home.title' },
    loadChildren: () => import('./task/task.routes'),
  },
  {
    path: 'team',
    data: { pageTitle: 'protaskinxxwApp.team.home.title' },
    loadChildren: () => import('./team/team.routes'),
  },
  {
    path: 'sprint',
    data: { pageTitle: 'protaskinxxwApp.sprint.home.title' },
    loadChildren: () => import('./sprint/sprint.routes'),
  },
  {
    path: 'notification',
    data: { pageTitle: 'protaskinxxwApp.notification.home.title' },
    loadChildren: () => import('./notification/notification.routes'),
  },
  {
    path: 'category',
    data: { pageTitle: 'protaskinxxwApp.category.home.title' },
    loadChildren: () => import('./category/category.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
