package wang.hubert.leetcode.design.raft;

public interface IState {
   void onEnterState();

   void onExitState();
}
