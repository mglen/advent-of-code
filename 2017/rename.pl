opendir(DIR,'.');
while($file = readdir(DIR)) {
	if ($file =~ m/(\d+)/) {
		rename($file, sprintf('day%02s',$1));
	}
}
