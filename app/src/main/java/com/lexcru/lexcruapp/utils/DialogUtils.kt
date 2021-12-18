package com.lexcru.lexcruapp.utils


object DialogUtils{
    /*fun showConfirmationDialog(
        context: Context,
        title: String? = null,
        message:String,
        positiveText: String,
        negativeText: String,
        icon: Int? = null,
        callback: DialogInterface.OnClickListener? = null,
        isCancelable: Boolean? = true
    ) {
        val confirmationAlertDialog = Dialog(context)
        confirmationAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        confirmationAlertDialog.setContentView(R.layout.dialog_two_button)
        confirmationAlertDialog.setCancelable(isCancelable.nullSafe(true))

        val tvTitle = confirmationAlertDialog.findViewById<AppCompatTextView>(R.id.tvTitle)
        val tvMessage = confirmationAlertDialog.findViewById<AppCompatTextView>(R.id.tvMessage)
        val btNegative = confirmationAlertDialog.findViewById<AppCompatButton>(R.id.btNegative)
        val btPositive = confirmationAlertDialog.findViewById<AppCompatButton>(R.id.btPositive)
        val ivIcon = confirmationAlertDialog.findViewById<AppCompatImageView>(R.id.ivIcon)

        if (title != null) {
            tvTitle.showView()
            tvTitle.text = title
        }

        if (icon != null) {
            ivIcon.showView()
            ivIcon.setImageResource(icon)
        }

        tvMessage.text = message

        btPositive.text = positiveText
        btNegative.text = negativeText

        btNegative.setOnClickListener {
            confirmationAlertDialog.dismiss()
            callback?.onClick(confirmationAlertDialog, DialogInterface.BUTTON_NEGATIVE)
        }

        btPositive.setOnClickListener {
            confirmationAlertDialog.dismiss()
            callback?.onClick(confirmationAlertDialog, DialogInterface.BUTTON_POSITIVE)
        }

        confirmationAlertDialog.show()
        confirmationAlertDialog.window!!.setLayout(
            context.resources.getDimension(R.dimen.confirmation_dialog_width).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }*/
}