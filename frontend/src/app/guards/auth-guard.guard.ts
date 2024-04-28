import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateFn,
  GuardResult,
  MaybeAsync, Router,
  RouterStateSnapshot
} from '@angular/router';
import {Injectable} from "@angular/core";
import {ServiceService} from "../service/service.service";

@Injectable()
export class AuthGuardGuard implements CanActivate{
  constructor(private service:ServiceService,private router:Router) {
  }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    if(this.service.isAuthenticated){
      return true;
    }else{
      this.router.navigateByUrl('/login')
      return false;
    }
  }

}
