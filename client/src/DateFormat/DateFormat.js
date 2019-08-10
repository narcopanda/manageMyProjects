export const formatDate = string => {
  var temp = new Date(string);

  return temp.toLocaleDateString(
    "en-US",
    { timeZone: "UTC" },
    {
      month: "numeric",
      day: "numeric",
      year: "numeric"
    }
  );
};
