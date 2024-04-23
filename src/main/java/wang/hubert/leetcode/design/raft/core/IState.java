package wang.hubert.leetcode.design.raft.core;

public interface IState {
   void onEnterState();

   void onExitState();
}
