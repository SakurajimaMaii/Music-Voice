package cn.govast.gmusic.ui.base

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/27
// Description: 
// Documentation:
// Reference:

interface UIStateListener {
    /**
     * 监听UI状态变化
     *
     * @see cn.govast.vasttools.livedata.base.State
     */
    fun initUIState()

    /**
     * 初始化VieModel监听
     */
    fun initUIObserver()

    /** 初始化UI */
    fun initUI()
}