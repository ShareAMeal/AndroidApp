public class hold {
}

//        TextView id = layout.findViewById(R.id.eventID);
//        TextView name = layout.findViewById(R.id.eventName);
//        final TextView mdate = layout.findViewById(R.id.eventStartDate);
//        final TextView mTime = layout.findViewById(R.id.eventStartTime);
//        Switch active = layout.findViewById(R.id.eventActive);
//        final TextView description = layout.findViewById(R.id.eventDescription);

//        id.setText(Integer.toString(list.get(position).getId()));
//        name.setText(list.get(position).getName());
//        mdate.setText(list.get(position).getStart_datetime().toString());
//        active.setChecked(list.get(position).isActive());
//        description.setText(list.get(position).getDescription());

//        Date getdate = list.get(position).getStart_datetime();
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm");
//        SimpleDateFormat yearFormat = new SimpleDateFormat("YYYY");
//        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
//        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
//        SimpleDateFormat hoursFormat = new SimpleDateFormat("hh");
//        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
//
//        final String year = yearFormat.format(getdate);
//        final String month = monthFormat.format(getdate);
//        final String day = dayFormat.format(getdate);
//        final String hours = hoursFormat.format(getdate);
//        final String min = minuteFormat.format(getdate);
//
//
//        Log.d(TAG, dateFormat.format(getdate));
//
//        mdate.setText(day + "/" + month + "/" + year); //A changer avec un format adaptatif
//        mTime.setText(hours + ":" + min);               // a changer avec un ofrmat adaptatif
//
//        final DatePickerDialog.OnDateSetListener mDateSetListenner = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int newyear, int newmonth, int newdayOfMonth) {
//
//                String date = newdayOfMonth + "/" + newmonth + "/" + newyear; //A changer avec un format adaptatif
//
//                // Ici on peut envoyer les changements a la BDD pour la date
//
//                mdate.setText(date);
//
//            }
//        };
//
//        final TimePickerDialog.OnTimeSetListener mTimeSetListenner = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int newhourOfDay, int newminute) {
//
//
//                String time = newhourOfDay + ":" + newminute; //A changer avec un format adaptatif
//
//                // ici on peut envoyer les changements a la BDD pour l'heure
//
//                mTime.setText(time);
//            }
//        };
//
//        mdate.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                DatePickerDialog dialog = new DatePickerDialog(
//                        context,
//                        mDateSetListenner,
//                        Integer.parseInt(year),
//                        Integer.parseInt(month),
//                        Integer.parseInt(day)
//                );
//                dialog.show();
//
//            }
//        });
//
//        mTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                TimePickerDialog dialog = new TimePickerDialog(
//                        context,
//                        mTimeSetListenner,
//                        Integer.parseInt(hours),
//                        Integer.parseInt(min),
//                        true);
//                dialog.show();
//            }
//        });
//
//        active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                list.get(position).active(isChecked);
//
//                // On peut changer la valeur Active dans la BDD
//            }
//        });
//
//        description.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AlertDialog.Builder(context)
//                        .setTitle(R.string.eventDescription)
//                        .setView(R.layout.changedescription)
//                        .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                //envoyer le changement de la description a la BDD
//
//                                EditText newDescription = layout.findViewById(R.id.editEventDescription);
//                                newDescription.setText(list.get(position).getDescription());
//
//                                Toast.makeText(context, "Good !", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.cancel();
//                            }
//                        })
//                        .show();
//            }
//        });
//